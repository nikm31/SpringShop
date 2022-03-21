angular.module('store').controller('editUserController', function ($scope, $http, $localStorage, $location) {
    const contextPath = $localStorage.contextPath + '/auth/api/v1/register';

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