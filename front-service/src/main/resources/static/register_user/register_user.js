angular.module('store').controller('registerUserController', function ($scope, $http, $location, $localStorage) {
    const contextPath =  $localStorage.contextPath + '/auth/api/v1/register';

    $scope.registerUser = function () {
        // if ($scope.newUser.password != $scope.newUser.passwordConfirmation) {
        //     alert("пароли не совпадают")
        //     $scope.newUser.password = ''
        //     $scope.newUser.passwordConfirmation = ''
        // }
        $http.post(contextPath, $scope.newUser)
            .then(function (response) {
                console.log(response)
                // alert('Вы успешно зарегистрированы');
                // $location.path('/store');
            });
    }

});