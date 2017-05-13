var myApp = angular.module("myApp", [ "slickCarousel","ngRoute"]);

myApp.config(function($routeProvider, $locationProvider) {
	$routeProvider

	.when('/', {
		templateUrl : 'pages/home.html',
		controller : 'homeCtrl'
	}).when('/wellness', {
		templateUrl : 'pages/wellness.html',
		controller : 'wellnessCtrl'
	}).when('/calendar', {
		templateUrl : 'pages/calendar.html',
		controller : 'calendarCtrl'
	}).when('/social', {
		templateUrl : 'pages/social.html',
		controller : 'socialCtrl'
	}).when('/logo', {
		templateUrl : 'pages/logo.html',
		controller : 'logoCtrl'
	}).otherwise('/');
	$locationProvider.html5Mode(true);
});
myApp.config(['slickCarouselConfig', function (slickCarouselConfig) {
    slickCarouselConfig.dots = true;
    slickCarouselConfig.autoplay = false;
  }])

myApp.factory('Fact', function() {
    return {
        Field : 'Not Scheduled yet'
    };
});

myApp.service('basicService', function() {
	var self = this;
	self.some = "some";
	self.page = '';
//    $scope.animate = function(){
//    	$.fn.extend({
//    	    animateCss: function (animationName) {
//    	        var animationEnd = 'webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend';
//    	        this.addClass('animated ' + animationName).one(animationEnd, function() {
//    	            $(this).removeClass('animated ' + animationName);
//    	        });
//    	    }
//    	});
//    }
});


myApp.controller('mainCtrl', function($scope, $rootScope, $location, $http, $timeout, Fact, basicService) {
	$rootScope.today = new Date();
	$rootScope.tomorrow = new Date();
	$rootScope.tomorrow.setDate($rootScope.tomorrow.getDate() + 1);
	var curHr = $rootScope.today.getHours();

	if (curHr < 12) {
		$scope.timeOfDay = 'Morning';
	} else if (curHr < 17) {
		$scope.timeOfDay = 'Afternoon';
	} else {
		$scope.timeOfDay = 'Evening';
	};
	$scope.userName = '';
	$rootScope.outData = [];
	$rootScope.slickConfig2Loaded = true;
	$rootScope.slickConfig2 = {
      autoplay: false,
      infinite: false,
      slidesToShow: 1,
      slidesToScroll: 1,
      variableWidth: true,
      centerMode: true,
      arrows: false,
      method: {}
    };
	$scope.menu = [ 
		{label : 'Ginni', route : '/tvdemo/', router:'/'},
		{label : 'Calendar', route : '/tvdemo/calendar', router:'/calendar'},
		{label : 'Wellness', route : '/tvdemo/wellness', router:'/wellness'},
		{label : 'Social', route : '/tvdemo/social', router:'/social'}
		]

	$scope.menuActive = '/tvdemo/';

	$rootScope.$on('$routeChangeSuccess', function(e, curr, prev) {
		$scope.menuActive = $location.path();
	});
	$scope.logoView = false;
	$scope.logoUrl = "/tvdemo/images/logo.png";
	$location.path('/logo');
	setInterval(ajaxCall, 2000);

    function ajaxCall() {
    	$http({
    		  method: 'GET',
    		  url: 'https://ec2-35-165-77-50.us-west-2.compute.amazonaws.com:8443/alexademo/v1/services/commands/current'
    		}).then(function successCallback(response) {
    			$rootScope.user = response.data.id;
    			$scope.logoView = true;
    			basicService.page = response.data.id;
				var command = basicService.page;
	
	
    		    if(command == "TOMORROW_CALENDAR"){
    		    	$location.path('/calendar');
    		    } else if(response.data.id == "CONTACING_DAUGHTER"){
    		    	$timeout(function() {
    		    		var rideElements = document.querySelectorAll('.ride');
    		        	var first = rideElements[0];
    		        	$(first).removeClass("red");
    		    		$rootScope.outData[0].ride = "Your daughter will pick you up at 1:00"; 
    		    		$(first).addClass("green");
    		    		}, 4500);
    		    } else if((command == "SHARE_PICTURE_YES") || (command == "UP_COMMING_EVENTS")){
    		    	$location.path('/social');
    		    } else if(response.data.id == "GOING_TO_BED"){
    		    	$location.path('/wellness');
    		    } else if(response.data.id == "NONE"){
    		    	$scope.userName = ", " + response.data.user;
    		    	$location.path('/');
    		    }
    		  }, function errorCallback(response) {
    		    // called asynchronously if an error occurs
    		    // or server returns response with an error status.
    		  });
    };
    
    $scope.reset =function() {
    	$http({
    		  method: 'POST',
    		  url: 'https://ec2-35-165-77-50.us-west-2.compute.amazonaws.com:8443/alexademo/v1/services/commands/reset'
    		}).then(function successCallback(response) {
    			//
    		  }, function errorCallback(response) {
    		    // called asynchronously if an error occurs
    		    // or server returns response with an error status.
    		  });
    };
	

});

myApp.controller('homeCtrl', ['$scope', '$rootScope', '$interval','$location','basicService', function($scope, $rootScope, $interval, $location, basicService) {
	
	$scope.dougFamilySlides = [{"image":"/tvdemo/images/1.jpg"},{"image":"/tvdemo/images/2.jpg"},{"image":"/tvdemo/images/3.jpg"},{"image":"/tvdemo/images/4.jpg"},{"image":"/tvdemo/images/5.jpg"},{"image":"/tvdemo/images/6.jpg"}];
	$scope.dougNatureSlides = [{"image":"/tvdemo/images/1.jpg"},{"image":"/tvdemo/images/2.jpg"},{"image":"/tvdemo/images/3.jpg"},{"image":"/tvdemo/images/4.jpg"},{"image":"/tvdemo/images/5.jpg"},{"image":"/tvdemo/images/6.jpg"}];
	$scope.steveFamilySlides = [{"image":"/tvdemo/images/1.jpg"},{"image":"/tvdemo/images/2.jpg"},{"image":"/tvdemo/images/3.jpg"},{"image":"/tvdemo/images/4.jpg"},{"image":"/tvdemo/images/5.jpg"},{"image":"/tvdemo/images/6.jpg"}];
	$scope.steveNatureSlides = [{"image":"/tvdemo/images/1.jpg"},{"image":"/tvdemo/images/2.jpg"},{"image":"/tvdemo/images/3.jpg"},{"image":"/tvdemo/images/4.jpg"},{"image":"/tvdemo/images/5.jpg"},{"image":"/tvdemo/images/6.jpg"}];
	$scope.slides = $scope.dougFamilySlides;
	
	$('.tlt').textillate({
		out: {
			effect: 'fadeOut'
		}
	});
	
	$('.star').addClass('zoomIn');
	
	$scope.currentIndex = 0;
	
	
	$scope.setCurrentSlideIndex = function(index) {
		$scope.currentIndex = index;
	};
	$scope.isCurrentSlideIndex = function(index) {
		return $scope.currentIndex === index;
	};
	$scope.nextSlide = function ($interval) {
		 $scope.currentIndex = ($scope.currentIndex > 0) ? --$scope.currentIndex : $scope.slides.length - 1;
    };
     var promise = $interval($scope.nextSlide, 60000);
     $scope.$on('$destroy',function(){
         if(promise)
             $interval.cancel(promise);   
     });
}]);

myApp.controller('socialCtrl', function($scope, $rootScope, Fact, $interval, $timeout, basicService) {
	//var command = basicService.page
	$scope.data = [{"id":"1","date":"Friday, April 21","name":"Movie Night","time":"06:00 PM - 09:00 PM", "where":"Lake Side Park", "img":"images/movie1.jpg"},{"id":"2","date":"Sunday, May 14","name":"Blossom Trail Hiking","time":"07:00 AM - 11:00 AM", "where":"Bus will start from Reception", "img":"images/hiking1.png"},{"id":"3","date":"Sunday, May 21","name":"Mother's Day Brunch","time":"12:00 PM - 1:00 PM",  "more": "Mother's day brunch will be in", "more2":" Holiday Touch Dining Room", "more3":" and free for community members. Brunch will be served from ", "more4": "12:00 to 1:00.", "more5": " No reservation required", "img":"images/mothers.jpg"}];
	 $interval(function() {
		 command = basicService.page;
		 $scope.commandResponder(command);
			}, 1000);
	 $scope.toggleInfo = "less";
	 $scope.commandResponder = function(command){
		 if(command == "SHARE_PICTURE_YES"){
			$scope.toggleSocial = 'image';
		} else if(command == "UP_COMMING_EVENTS"){
			$rootScope.slickConfig2.method.slickGoTo(0);
			$scope.toggleSocial = 'slider';
		} else if(command == "SECOND_EVENT"){
			$scope.toggleSocial = 'slider';
			$rootScope.slickConfig2.method.slickGoTo(1);
		} else if(command == "LAST_EVENT"){
			$scope.toggleSocial = 'slider';
			$rootScope.slickConfig2.method.slickGoTo(2);
		} else if(command == "MOTHER_BRUNCH"){
			$scope.toggleSocial = 'slider';
			$rootScope.slickConfig2.method.slickGoTo(2);
			$(".sliderImg3").slideUp();
			$scope.toggleInfo = "more";
		} else if(command == "ADDING_BRUNCH"){
			//console.log("5 :: " + command);
		}
	 };
});

myApp.controller('wellnessCtrl', function($scope, $rootScope, Fact, $interval, $timeout, basicService) {    
		$scope.data = [{"date":$rootScope.today, "_id":"1", "med1":"30mg Monopril", "med2":"30mg Lipitor"},{"date":$rootScope.tomorrow, "_id":"2", "med1":"20mg Lisinopril", "med2":"30mg Lipitor", "med3":"10mg Atrovin"}];
		$rootScope.outData = $scope.data;
		//GOOD_NIGHT_MSG, GOING_TO_BED	

});

myApp.controller('calendarCtrl', function($scope, $rootScope, Fact, $interval, $timeout, basicService) {    
	$scope.data = [{"date":$rootScope.tomorrow, "_id":"1","index":0,"appointment":"Dr. Miller","time":"01:30 p.m.", "ride":"Not scheduled yet"},{"date":$rootScope.tomorrow, "_id":"2","index":1,"appointment":"Movie with Daughter","time":"04:00 p.m.", "ride":"Ride scheduled with Lyft"}];
	if(cdac){
		$scope.danceImg = "/tvdemo/images/dance.png";
	}
	$rootScope.outData = $scope.data;
//    $interval(function() {
//    	console.log("Page ::  " + basicService.page);
//    	data[0].ride = basicService.page;
//		}, 1000);

    $timeout(function() {
    	$rootScope.slickConfig2.method.slickGoTo(1);
		}, 3000);
    $timeout(function() {
    	$rootScope.slickConfig2.method.slickGoTo(0);
    	var redElements = document.querySelectorAll('.ride');
    	var first = redElements[0];
    	$(first).addClass("red");
		}, 7000);

});
