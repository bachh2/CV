const URLSEARCH =
    "https://api.unsplash.com/search/photos/?client_id=f7N-c7ynV9x6FAE3c1mP35-_1uRQeFNKMYlRro55XGA&page=";
const URL =
    "https://api.unsplash.com/photos/?client_id=f7N-c7ynV9x6FAE3c1mP35-_1uRQeFNKMYlRro55XGA&page=";
let currentPage = 1;
let page = 1;
let pageS = 1;
let currentURL = URL;
let $text = $(".form - control").val();

function loadImage(url, page, text = "") {
    const templateEl = document.querySelector(".templateItem").content;
    const $listItem = $(".listItem");
    if (!text) {
        $.ajax(url + page).done(function(data) {
            $.each(data, function(index, product) {
                const $product = $(templateEl).clone();
                $product.find(".infor").text(product.user.name);
                $product.find(".thumbnail").attr("src", product.urls.small);
                $product.find(".numberLike").text("Liked: " + product.likes);
                $product.appendTo($listItem);
            });
        });
    } else {
        $.ajax(url + page + "&query=" + text).done(function(data) {
            $.each(data.results, function(index, product) {
                const $product = $(templateEl).clone();
                $product.find(".infor").text(product.user.name);
                $product.find(".thumbnail").attr("src", product.urls.small);
                $product.find(".numbleLike").text("Liked: " + product.likes);
                $product.appendTo($listItem);
            });
        });
    }
}

function scroll() {
    $(window).on("scroll", function(e) {
        e.preventDefault();
        if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
            setTimeout(loadImage(currentURL, currentPage, $text), 200);
            currentPage++;
        }
    });
}
$(function() {
    loadImage(currentURL, currentPage);
    scroll(currentURL, currentPage, $text);
    $(".form-group").on("submit", function(e) {
        e.preventDefault();
        $("div").remove(".photo-detail");
        $text = $(".form-control").val();
        currentURL = URLSEARCH;
        currentPage = pageS;
        loadImage(currentURL, currentPage, $text);
        currentPage++;
        scroll();
        currentPage++;
    });
    $(".form-group").on("keyup", function(e) {
        $text = $(".form-control").val();
        if ($text === "") {
            $("div").remove(".photo-detail");
            currentURL = URL;
            currentPage = 1;
            pageS = 1;
            loadImage(currentURL, currentPage, $text);
        }
    });
});
