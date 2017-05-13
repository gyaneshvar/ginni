angular.module("calendar").service('CalendarService', function($http) {
	this.exampleGET = function() {
		

		var path = 'https://35.165.77.50:8443/smg/v1/services';

		return $http({
			method: 'GET',
			url: path
		});
	};
});
