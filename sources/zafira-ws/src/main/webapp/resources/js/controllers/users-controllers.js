'use strict';

ZafiraApp.controller('UsersListCtrl', [ '$scope', '$rootScope', '$http' ,'$location', '$modal', '$route', 'DashboardService', function($scope, $rootScope, $http, $location, $modal, $route, DashboardService) {

	const DEFAULT_SC = {
			'page' : 1,
			'pageSize' : 20
	};
	
	$scope.usersSearchCriteria = angular.copy(DEFAULT_SC);
	
	$scope.totalResults = 0;
	$scope.users = [];
	$scope.pefrDashboardId = null;
	
	$scope.loadUsers = function(page, pageSize){
		
		$scope.usersSearchCriteria.page = page;
		if(pageSize)
		{
			$scope.usersSearchCriteria.pageSize = pageSize;
		}
		
		$http.post('users/search', $scope.usersSearchCriteria).then(function successCallback(data) {
			var data = data.data;
			$scope.usersSearchCriteria.page = data.page;
			$scope.usersSearchCriteria.pageSize = data.pageSize;
			$scope.users = data.results;
			$scope.totalResults = data.totalResults;
		}, function errorCallback(data) {
			console.error('Failed to search users');
		});
	};
	
	$scope.openUserDetailsModal = function(id){
		if(id)
		{
			$http.get('users/' + id).then(function successCallback(user) {
				var user = user.data;
				$modal.open({
					templateUrl : 'resources/templates/user-details-modal.jsp',
					resolve : {
						'id' : function(){
							return id;
						},
						'user' : function(){
							return user;
						}
					},
					controller : function($scope, $modalInstance, id, user){
						
						$scope.id = id;
						$scope.user = user;
		
						$scope.updateUser = function(user){
							$http.put('users', user).then(function successCallback(data) {
								$route.reload();
							}, function errorCallback(data) {
								alertify.error('Failed to update user');
							});
							$modalInstance.close(0);
						};
						
						$scope.deleteUser = function(user){
							$http.delete('users/' + user.id).then(function successCallback(data) {
								$route.reload();
							}, function errorCallback(data) {
								alertify.error('Failed to delete user');
							});
							$modalInstance.close(0);
						};
						
						$scope.cancel = function(){
							$modalInstance.close(0);
						};
					}
				});
			}, function errorCallback(data) {
				console.error('Failed to load user');
			});
		}
		else
		{
			$modal.open({
				templateUrl : 'resources/templates/user-details-modal.jsp',
				controller : function($scope, $modalInstance){
					
					$scope.user = {};
	
					$scope.updateUser = function(user){
						$http.put('users', user).then(function successCallback(data) {
							$route.reload();
						}, function errorCallback(data) {
							alertify.error('Failed to update user');
						});
						$modalInstance.close(0);
					};
					
					$scope.cancel = function(){
						$modalInstance.close(0);
					};
				}
			});
		}
	};

	
	$scope.resetSearchCriteria = function(){
		$scope.usersSearchCriteria = angular.copy(DEFAULT_SC);
	};
	
	(function init(){
		$scope.loadUsers(1);
		DashboardService.getUserPerformanceDashboardId().then(function(dashboardId) {
			$scope.pefrDashboardId = dashboardId;
		});
	})();
	
}]);

ZafiraApp.controller('UsersProfileCtrl', [ '$scope', '$rootScope', '$http' ,'$location', '$modal', '$route', 'UserService', function($scope, $rootScope, $http, $location, $modal, $route, UserService) {

	$scope.user = {};
	
	$scope.updateUser = function(user){
		$http.put('users', user).then(function successCallback(data) {
			$route.reload();
		}, function errorCallback(data) {
			alertify.error('Failed to update user');
		});
	};
	
	$scope.updatePassword = function(newPassword, confirmPassword){
		if(newPassword == confirmPassword)
		{
			$scope.user.password = newPassword;
			$scope.updateUser($scope.user);
		}
		else
		{
			alertify.error("Passwords does not match!");
		}
	};
	
	(function init(){
		UserService.getCurrentUser().then(function(user) {
			$http.get('users/' + user.id).then(function successCallback(data) {
				$scope.user = data.data;
				$scope.user.password = null;
			}, function errorCallback(data) {
				alertify.error('Failed to load user');
			});
		});
	})();
	
}]);