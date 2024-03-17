from interfaces.candidate_management_interface import CandidateManagementInterface
from interfaces.job_management_interface import JobManagementInterface
from interfaces.communication_interface import CommunicationInterface

class RecruitmentModule:
    def __init__(self, candidate_manager: CandidateManagementInterface, 
                       job_manager: JobManagementInterface,
                       communication_manager: CommunicationInterface):
        self.candidate_manager = candidate_manager
        self.job_manager = job_manager
        self.communication_manager = communication_manager

    def process_recruitment(self, candidate_id, job_id):
        candidate = self.candidate_manager.get_candidate(candidate_id)
        job = self.job_manager.get_job(job_id)
        
        if candidate is None:
            print(f"Error: Candidate with ID {candidate_id} not found.")
            return
        
        if job is None:
            print(f"Error: Job with ID {job_id} not found.")
            return
        
        message = "Congratulations! You've been shortlisted for the job."
        self.communication_manager.send_email(candidate, message)
