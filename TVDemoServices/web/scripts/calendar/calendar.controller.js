angular.module("calendar",[]).controller('CalendarController', function($scope, CalendarService, $location) {
	$scope.data = {};

	$scope.initialize = function() {
		CalendarService.exampleGET().then(function(response) {
			$scope.data = response.data;
		});
	};
	console.log($location.path());

	$scope.initialize();
});
