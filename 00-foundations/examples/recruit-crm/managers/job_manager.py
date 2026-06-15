from interfaces.job_management_interface import JobManagementInterface


class JobManager(JobManagementInterface):
    def __init__(self):
        self.jobs = {}

    def post_job(self, job):
        self.jobs[job["id"]] = job

    def update_job(self, job):
        self.jobs[job["id"]] = job

    def get_job(self, job_id):
        return self.jobs.get(job_id)
