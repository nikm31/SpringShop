angular.module('store').controller('editProductController', function ($scope, $http, $routeParams, $location, $localStorage) {


    $scope.prepareProductForUpdate = function () {
        $http.get($localStorage.contextPath + 'api/v1/products/' + $routeParams.productId)
            .then(function successCallback (response) {
                $scope.updated_product = response.data;
            }, function failureCallback (response) {
                console.log(response);
                alert(response.data.messages);
                $location.path('/store');
            });
    }

    $scope.updateProduct = function () {
        $http.put($localStorage.contextPath + 'api/v1/products', $scope.updated_product)
            .then(function successCallback (response) {
                $scope.updated_product = null;
                alert('Продукт успешно обновлен');
                $location.path('/store');
            }, function failureCallback (response) {
                alert(response.data.messages);
            });
    }

    $scope.prepareProductForUpdate();
});