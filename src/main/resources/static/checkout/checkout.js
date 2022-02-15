angular.module('store').controller('checkoutController', function ($scope, $http, $location, $localStorage) {

    $scope.loadCart = function () {
        var id = $localStorage.contextPath + '/api/v1/cart/' + $localStorage.webMarketGuestCartId
        console.log(id)
        $http({
            url: $localStorage.contextPath + '/api/v1/cart/' + $localStorage.webMarketGuestCartId,
            method: 'GET'
        }).then(function (response) {
            $scope.cart = response.data;
        });
    };

    $scope.createOrder = function () {
        $http({
            url: $localStorage.contextPath + '/api/v1/orders',
            method: 'POST',
            data: $scope.orderDetails
        }).then(function (response) {
            alert('Ваш заказ успешно сформирован');
            $location.path('/');
        });
    };

    $scope.loadCart();
});