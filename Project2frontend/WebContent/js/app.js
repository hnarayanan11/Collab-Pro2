/**
 * Angular JS modeul
 */
var app=angular.module("app",['ngRoute','ngCookies'])

app.config(function($routeProvider){
	$routeProvider
	.when('/registration',{controller:'UserCtrl',templateUrl:'views/registrationform.html'})
	.when('/login',{controller:'UserCtrl',templateUrl:'views/login.html'})
	.when('/home',{templateUrl:'views/home.html'})
	.when('/getalljobs',{controller:'UserCtrl',templateUrl:'views/jobs.html'})
	.otherwise({templateUrl:'views/home.html'})
})
// Angular module gets instantiated, app.run() will get executed
// restore userdetails to the varibale user in $rootScope
app.run(function($rootScope, $location, $cookieStore,  UserService){
	if($rootScope.user==undefined)
		$rootScope.user=$cookieStore.get('userDetails')
		
	$rootScope.logout=function(){
		alert('Entering into logout')
		UserService.logout().then(function(response){
			// remove the user variable from $rootScope
			// clear the cookie
			// redirect the user to login page
			console.log('successfully logged out')
			delete $rootScope.user
			$cookieStore.remove('userDetatils')
			$location.path('/login')
		},function(response){
			// if $rootScope.user is ot undefined, clear the cookie and redirect
			// the user to login page
			console.log('email is null')
			if($rootScope.user!=undefined)
			delete $rootScope.user
			$cookieStore.remove('userDetatils')
			$location.path('/login')
		})
	}
})