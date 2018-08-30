/**
 * JobCtrl
 */
app.controller('JobCtrl',
		function($scope, $routeParams, JobService, $location) {
			$scope.showJob = false;
			var id=$routeParams.id
			// Data is from View to Controller
			$scope.addJob = function(job) {
				JobService.addJob(job).then(function(response) {
					alert('Job Details insertted successfully...')
					$location.path('/getalljobs')
				}, function(response) {
					if (response.data.errorCode == 4)
						$location.path('/login')
					$scope.error = response.data
				})
			}

			// Data is from Controller to View
			// Statement which will get executed always
			function getAllJobs() {
				JobService.getAllJobs().then(function(response) {
					$scope.jobs = response.data
				}, function(response) {
					if (response.status == 401)
						$location.path('/login')
				})
			}

			$scope.showJobDetails = function(id) {
				$scope.id = id // job id
				$scope.showJob = !$scope.showJob/* !$scope.showJob */
			}

			$scope.deleteJob = function(id) {
				JobService.deleteJob(id).then(function(Response) {
					getAllJobs()
				}, function(Response) {
					if (Response.status == 401)
						$location.path('/login')
					$scope.error = Response.data
				})
			}

			if ($routeParams.id != undefined)
				console.log('Job based on ID ' + id)
			JobService.updateJobForm(id).then(function(Response) {
				$scope.job = Response.data
				console.log($scope.job)
				console.log(Response.data)				
			}, function(Response) {
				if (Response.status == 401)
					$location.path('/login')
				$scope.error = Response.data
			})

			$scope.updateJob = function(jobs) {
				JobService.updateJob(jobs).then(function(Response) {
					$location.path('/getalljobs')
				}, function(Response) {
					if (Response.status == 401)
						$location.path('/login')
					$scope.error = Response.data
				})
			}

			getAllJobs()
		})