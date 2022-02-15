angular.module('store').controller('addProductController', function ($scope, $http, $localStorage, $location) {

    $scope.loadCategories = function () {
        $http.get($localStorage.contextPath + '/api/v1/categories')
            .then(function (response) {
            $scope.categories = response.data;});
    };

    $scope.loadCategories();

    $scope.saveProduct = function (){

    };
});