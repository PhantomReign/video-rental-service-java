/**
 * Created by Rave on 27.04.2017.
 */
function googleMapInit() {
    var myLatLng = {
        lat: 49.1864516,
        lng: 18.8611313
    };

    var map = new google.maps.Map(document.getElementById('googleMap'), {
        zoom: 15,
        draggable: false,
        center: myLatLng
    });

    var marker = new google.maps.Marker({
        position: myLatLng,
        map: map,
        title: 'Videopožičovňa Saturn'
    });

    marker.setMap(map);
}