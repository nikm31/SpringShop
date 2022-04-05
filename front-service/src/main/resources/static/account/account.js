angular.module('store').controller('accountController', function ($scope, $http, $location, $localStorage) {
    const contextPath =  $localStorage.contextPath + '/core/api/v1/account';
    $scope.userName = $localStorage.webMarketUser.username;
    $scope.userRoles = $localStorage.webMarketUser.roles;

    $scope.showUserInfo = function () {
    }

    $scope.showUserInfo();
});