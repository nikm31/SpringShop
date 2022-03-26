angular.module('store').controller('favoriteProductsController', function ($scope, $http, $location, $localStorage) {

    $scope.loadFavorite = function () {
        var id = $localStorage.contextPath + '/favorite/api/v1/favorite/' + $localStorage.webMarketGuestCartId
        console.log(id)
        $http({
            url: $localStorage.contextPath + '/favorite/api/v1/favorite/' + $localStorage.webMarketGuestCartId,
            method: 'GET'
        }).then(function (response) {
            console.log(response)
            $scope.cart = response.data;
        });
    };

    $scope.removeFromFavorite = function (productId) {
        $http({
            url: $localStorage.contextPath + '/favorite/api/v1/favorite/' + $localStorage.webMarketGuestCartId + '/delete/' + productId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadFavorite();
        });
    };

    $scope.removeAll = function (productId) {
        $http({
            url: $localStorage.contextPath + '/favorite/api/v1/favorite/' + $localStorage.webMarketGuestCartId + '/delete/',
            method: 'GET'
        }).then(function (response) {
            $scope.loadFavorite();
        });
    };

    $scope.loadFavorite();
});