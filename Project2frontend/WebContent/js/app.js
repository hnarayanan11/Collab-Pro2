/**
 * Angular JS modeul
 */
var app=angular.module("app",['ngRoute'])

app.config(function($routeProvider){
	$routeProvider
	.when('/registration',{controller:'UserCtrl',templateUrl:'views/registrationform.html'})
	.when('/login',{controller:'UserCtrl',templateUrl:'views/login.html'})
	.when('/home',{templateUrl:'views/home.html'})
	.when('/getalljobs',{controller:'UserCtrl',templateUrl:'views/jobs.html'})
	.otherwise({templateUrl:'views/home.html'})
})