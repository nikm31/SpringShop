angular.module('store').controller('storeController', function ($scope, $http, $location, $localStorage) {
    let currentPageIndex = 1;

    $scope.showProducts = function (pageIndex = 1) {
        currentPageIndex = pageIndex;
        $http({
            url: $localStorage.contextPath + '/api/v1/products',
            method: 'GET',
            params: {
                page: currentPageIndex,
                pageSize: 5,
                title: $scope.title,
                minPrice: $scope.minPrice,
                maxPrice: $scope.maxPrice
            }
        }).then(function (response) {
            console.log(response);
            $scope.productsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.productsPage.totalPages);
        })
    }

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.deleteProduct = function (productId) {
        let url = $localStorage.contextPath + '/api/v1/products/' + productId;
        console.log(url);
        $http.delete(url)
            .then(function successCallback(response) {
                alert('Продукт успешно удален');
                $scope.showProducts(currentPageIndex);
            }, function failureCallback(response) {
                alert(response.data.messages);
            });
    }

    $scope.addToCart = function (productId) {
        $http({
            url: $localStorage.contextPath + '/api/v1/cart/' + $localStorage.webMarketGuestCartId + '/add/' + productId,
            method: 'GET'
        })
    };

    $scope.addToFavorite = function (productId) {
        $http({
            url: $localStorage.contextPath + '/api/v1/favorite/' + $localStorage.webMarketGuestCartId + '/add/' + productId,
            method: 'GET'
        })
    };

    $scope.navToEditProductPage = function (productId) {
        $location.path('/edit_product/' + productId);
    }

    $scope.showProducts();
});