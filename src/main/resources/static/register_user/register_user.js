angular.module('store').controller('registerUserController', function ($scope, $http, $location, $localStorage) {
    const contextPath =  $localStorage.contextPath + '/api/v1/register';

    $scope.registerUser = function () {
        if ($scope.newUser.password != $scope.newUser.passwordConfirm) {
            alert("пароли не совпадают")
            $scope.newUser.password = ''
            $scope.newUser.passwordConfirm = ''
        }
        $http.post(contextPath, $scope.newUser)
            .then(function successCallback (response) {
                alert('Вы успешно зарегистрированы');
                $location.path('/store');
            }, function failureCallback (response) {
                alert(response.data.messages);
            });
    }

});