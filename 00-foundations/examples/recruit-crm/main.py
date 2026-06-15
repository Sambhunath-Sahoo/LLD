from managers.candidate_manager import CandidateManager
from managers.job_manager import JobManager
from managers.communication_manager import CommunicationManager
from recruitment_module import RecruitmentModule

if __name__ == "__main__":
    candidate_manager = CandidateManager()
    job_manager = JobManager()
    communication_manager = CommunicationManager()

    candidate_manager.add_candidate(
        {"id": 123, "name": "John Doe", "email": "john@example.com", "phone": "123-456-7890"}
    )
    job_manager.post_job(
        {
            "id": 456,
            "title": "Software Engineer",
            "description": "Full-time software engineer position",
            "location": "New York",
        }
    )

    recruitment_module = RecruitmentModule(
        candidate_manager, job_manager, communication_manager
    )
    recruitment_module.process_recruitment(candidate_id=123, job_id=456)
