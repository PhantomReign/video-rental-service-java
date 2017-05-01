package com.videorentalservice.controllers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.NumberExpression;
import com.videorentalservice.controllers.abstracts.AbstractBaseController;
import com.videorentalservice.models.Disc;
import com.videorentalservice.models.QDisc;
import com.videorentalservice.services.CategoryService;
import com.videorentalservice.services.DiscService;
import com.videorentalservice.services.GenreService;
import com.videorentalservice.utilities.SecurityUtility;
import com.videorentalservice.utilities.UploadUtility;
import com.videorentalservice.validators.DiscValidator;
import com.videorentalservice.validators.ImageValidator;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.videorentalservice.models.QUser.user;

/**
 * Created by Rave on 24.03.2017.
 */

@Controller
@Secured(SecurityUtility.MANAGE_DISC)
public class DiscManageController extends AbstractBaseController {

    private DiscService discService;
    private CategoryService categoryService;
    private GenreService genreService;

    @Autowired
    private DiscValidator discValidator;

    @Autowired
    private ImageValidator imageValidator;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public void setDiscService(DiscService discService) {
        this.discService = discService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @ModelAttribute("allCategories")
    public List<?> categoriesList(){
        return categoryService.listAll();
    }

    @ModelAttribute("allGenres")
    public List<?> genresList(){
        return genreService.listAll();
    }


    @RequestMapping(value = "/admin/movies", method = RequestMethod.GET)
    public String listAdminDiscs(Model model,
                            @QuerydslPredicate(root = Disc.class) Predicate predicate,
                            @PageableDefault(sort = { "title", "originalTitle" }, value = 8) Pageable pageable,
                            @RequestParam MultiValueMap<String, String> parameters) {

        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replaceQueryParam("page");

        model.addAttribute("discs", discService.findAll(predicate, pageable));
        model.addAttribute("genres", genreService.listAll());
        model.addAttribute("categories", categoryService.listAll());

        return "disc/discs-admin";
    }


    @RequestMapping("admin/movie/edit/{id}")
    public String editDisc(@PathVariable Integer id, Model model){
        model.addAttribute("disc", discService.getById(id));
        return "disc/disc-form-update";
    }

    @RequestMapping("admin/movie/new")
    public String newDisc(Model model){
        model.addAttribute("disc", new Disc());
        return "disc/disc-form-create";
    }

    @RequestMapping("admin/movie/show/{id}")
    public String showDiscAdmin(@PathVariable Integer id, Model model){
        model.addAttribute("disc", discService.getById(id));
        return "disc/disc-show-admin";
    }

    @RequestMapping(value = "/admin/movie/new", method = RequestMethod.POST)
    public String saveDisc(@Valid @ModelAttribute("disc") Disc disc, BindingResult result,
                           @RequestParam("fileCover") MultipartFile fileCover,
                           @RequestParam("fileBG") MultipartFile fileBG,
                           RedirectAttributes redirectAttributes){

        discValidator.validate(disc, result);
        imageValidator.validate(fileBG, result);
        imageValidator.validate(fileCover, result);
        if(result.hasErrors()){
            return "disc/disc-form-create";
        }

        int currentID = discService.findMaxId() + 1;

        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(accessKey, secretKey)
                )).withRegion(Regions.EU_CENTRAL_1).build();

        if (s3client == null) {
            redirectAttributes.addFlashAttribute("error", "Amazon AWS nie je k dispozícii.");
            return "redirect:/admin/movies";
        }

        String coverName = "movies/" + currentID + "/cover" + UploadUtility.extension(fileCover);
        String bgName = "movies/" + currentID + "/background" + UploadUtility.extension(fileBG);

        String coverURL = UploadUtility.uploadToS3(bucket, fileCover, coverName, s3client, disc);
        String bgURL = UploadUtility.uploadToS3(bucket, fileBG, bgName, s3client, disc);

        if (coverURL.equals("error") || bgURL.equals("error")) {
            redirectAttributes.addFlashAttribute("error", "Chyba pri nahrávaní obrázka.");
            return "redirect:/admin/movies";
        }

        disc.setImageUrl(coverURL);
        disc.setImageBGUrl(bgURL);

        discService.save(disc);
        redirectAttributes.addFlashAttribute("info", "Disk bol úspešne vytvorený.");
        return "redirect:/admin/movies";
    }

    @RequestMapping(value = "/admin/movie/edit/{id}", method = RequestMethod.POST)
    public String updateDisc(@Valid @ModelAttribute("disc") Disc disc,
                             BindingResult result,
                             @RequestParam("fileCover") MultipartFile fileCover,
                             @RequestParam("fileBG") MultipartFile fileBG,
                             RedirectAttributes redirectAttributes){

        Boolean updateCover = false;
        Boolean updateBG = false;

        discValidator.validate(disc, result);

        if (!fileCover.isEmpty() || fileCover.getSize() > 0){
            imageValidator.validate(fileCover, result);
            updateCover = true;
        }

        if (!fileBG.isEmpty() || fileBG.getSize() > 0){
            imageValidator.validate(fileBG, result);
            updateBG = true;
        }

        if(result.hasErrors()){
            return "disc/disc-form-update";
        }

        Disc saved = discService.getById(disc.getId());
        disc.setImageBGUrl(saved.getImageBGUrl());
        disc.setImageUrl(saved.getImageUrl());

        if (updateBG || updateCover) {
            AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(
                            new BasicAWSCredentials(accessKey, secretKey)
                    )).withRegion(Regions.EU_CENTRAL_1).build();

            if (s3client == null) {
                redirectAttributes.addFlashAttribute("error", "Amazon AWS nie je k dispozícii.");
                return "redirect:/admin/movies";
            }

            String coverName = "";
            String coverURL = "";
            String bgName = "";
            String bgURL = "";


            if (updateCover) {
                coverName= "movies/" + disc.getId() + "/cover" + UploadUtility.extension(fileCover);
                coverURL = UploadUtility.uploadToS3(bucket, fileCover, coverName, s3client, disc);
            }
            if (updateBG) {
                bgName = "movies/" + disc.getId() + "/background" + UploadUtility.extension(fileBG);
                bgURL = UploadUtility.uploadToS3(bucket, fileBG, bgName, s3client, disc);
            }

            if (coverURL.equals("error") || bgURL.equals("error")) {
                redirectAttributes.addFlashAttribute("error", "Chyba pri nahrávaní obrázka.");
                return "redirect:/admin/movies";
            }

            if (updateCover) {
                disc.setImageUrl(coverURL);
            }
            if (updateBG) {
                disc.setImageBGUrl(bgURL);
            }
        }

        discService.update(disc);
        redirectAttributes.addFlashAttribute("info", "Disk bol úspešne upravený.");
        return "redirect:/admin/movies";
    }


    @RequestMapping("admin/movie/delete/{id}")
    public String deleteDisc(@PathVariable Integer id,
                             RedirectAttributes redirectAttributes){
        Disc disc = discService.getById(id);
        if (!disc.getGenres().isEmpty() || !disc.getOrders().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Tento film nie je možné vymazať.");
            return "redirect:/admin/movies";
        }
        /*AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(accessKey, secretKey)
                )).withRegion(Regions.EU_CENTRAL_1).build();

        if (s3client == null) {
            redirectAttributes.addFlashAttribute("error", "Amazon AWS nie je k dispozícii.");
            return "redirect:/admin/movies";
        }

        Disc savedDisc = discService.getById(id);

        String coverKey = "movies/" + id + savedDisc.getImageUrl().substring(savedDisc.getImageUrl().lastIndexOf("/"));
        String bgKey = "movies/" + id + savedDisc.getImageBGUrl().substring(savedDisc.getImageBGUrl().lastIndexOf("/"));

        UploadUtility.deleteFromS3(bucket, coverKey, s3client);
        UploadUtility.deleteFromS3(bucket, bgKey, s3client);*/

        discService.delete(id);
        redirectAttributes.addFlashAttribute("info", "Disk bol úspešne vymazaný.");
        return "redirect:/admin/movies";
    }
}
