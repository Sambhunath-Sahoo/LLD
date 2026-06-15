from interfaces.communication_interface import CommunicationInterface


class CommunicationManager(CommunicationInterface):
    def send_email(self, candidate, message):
        print(f"Sending email to {candidate['email']}: {message}")

    def send_sms(self, candidate, message):
        print(f"Sending SMS to {candidate['phone']}: {message}")
