package ca.mcgill.ecse321.museum.dto;

/**
 * Ticket DTO
 *
 * @author Zahra
 */
public class TicketDto {

  private long ticketId;
  private String visitDate;
  private VisitorDto visitor;

  public TicketDto() {

  }

  public TicketDto(long ticketId, String visitDate, VisitorDto visitor) {
    this.ticketId = ticketId;
    this.visitDate = visitDate;
    this.visitor = visitor;

  }

  public TicketDto(String visitDate, VisitorDto visitor) {
    this.visitDate = visitDate;
    this.visitor = visitor;
  }

  public VisitorDto getVisitor() {
    return visitor;
  }

  public void setVisitor(VisitorDto visitor) {
    this.visitor = visitor;
  }

  public long getTicketId() {
    return ticketId;
  }

  public String getVisitDate() {
    return visitDate;
  }

  public void setTicketId(long ticketId) {
    this.ticketId = ticketId;
  }

  public void setVisitDate(String visitDate) {
    this.visitDate = visitDate;
  }
}
