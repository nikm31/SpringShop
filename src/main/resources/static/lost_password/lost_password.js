angular.module('store').controller('lostPasswordController', function ($scope, $http, $localStorage) {
    const contextPath = $localStorage.contextPath + '/api/v1/recover_password';

    $scope.changePassword = function () {
        $http.put(contextPath, $scope.updatePassword)
            .then(function successCallback (response) {
                alert('Пароль заменен');
            }, function failureCallback (response) {
                alert(response.data.messages);
            });
    }

});