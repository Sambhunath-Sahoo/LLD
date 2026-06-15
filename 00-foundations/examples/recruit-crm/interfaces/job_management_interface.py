from abc import ABC, abstractmethod


class JobManagementInterface(ABC):
    @abstractmethod
    def post_job(self, job):
        pass

    @abstractmethod
    def update_job(self, job):
        pass

    @abstractmethod
    def get_job(self, job_id):
        pass
