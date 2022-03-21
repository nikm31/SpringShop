angular.module('store').controller('ordersController', function ($scope, $http, $location, $localStorage) {

    $scope.loadOrders = function () {
        $http({
            url: $localStorage.contextPath + '/core/api/v1/orders',
            method: 'GET'
        }).then(function (response) {
            $scope.orders = response.data;
            console.log($scope.orders);
        });
    };

    $scope.loadOrders();
});