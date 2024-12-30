package com.prekdu;

/*
 * Design a Library Management System that handles different types of library resources
(books, digital content, periodicals). The system should demonstrate(Use enums, classes, functions etc):

1. Create an abstract class LibraryResource that contains:
   - Protected fields for resourceId, title, and availabilityStatus
   - Abstract method calculateLateFee(int daysLate)
   - Abstract method getMaxLoanPeriod()

2. Implement different types of resources:
   - Book: Has additional fields for author and ISBN
   - DigitalContent: Has fields for fileSize and format (PDF, EPUB)
   - Periodical: Has fields for issueNumber and frequency (WEEKLY, MONTHLY)

3. Create an interface Renewable with method:
   - boolean renewLoan(LibraryMember member)

4. Create an interface Reservable with methods:
   - void reserve(LibraryMember member)
   - void cancelReservation(LibraryMember member)

5. Implement a LibraryMember class that:
   - Maintains a list of borrowed resources
   - Has a membership type (STANDARD, PREMIUM)
   - Has methods to borrow and return resources

6. Implement proper exception handling for:
   - ResourceNotAvailableException
   - MaximumLoanExceededException
 - InvalidMembershipException

Requirements:
- Books and Periodicals should be both Renewable and Reservable
- DigitalContent should only be Renewable
- Different resource types should have different late fees and loan periods
- Premium members should have higher loan limits and lower late fees and loan periods
- Implement proper validation and business logic

 */

import java.util.ArrayList;
import java.util.List;

/** Enum for Content Format. */
enum ContentFormat {
  PDF,
  EPUB
}

/** Enum for Frequency. */
enum Frequency {
  WEEKLY,
  MONTHLY
}

/** Enum for Membership Types. */
enum MembershipType {
  STANDARD,
  PREMIUM
}

/** Enum for Resource Status. */
enum ResourceStatus {
  AVAILABLE,
  BORROWED,
  RESERVED
}

/** Exception for unavailable resources. */
class ResourceNotAvailableException extends Exception {
  public ResourceNotAvailableException(final String message) {
    super(message);
  }
}

/** Exception for exceeding maximum loan limits. */
class MaximumLoanExceededException extends Exception {
  public MaximumLoanExceededException(final String message) {
    super(message);
  }
}

/** Exception for invalid membership. */
class InvalidMembershipException extends Exception {
  public InvalidMembershipException(final String message) {
    super(message);
  }
}

/** Abstract class representing library resources. */
abstract class LibraryResource {
  private String resourceId;
  private String title;
  private ResourceStatus status;

  public LibraryResource(String resourceId, String title) {
    this.resourceId = resourceId;
    this.title = title;
    this.status = ResourceStatus.AVAILABLE;
  }

  public abstract double calculateLateFee(int daysLate);

  public abstract int getMaxLoanPeriod();

  public ResourceStatus getStatus() {
    return status;
  }

  public void setStatus(ResourceStatus status) {
    this.status = status;
  }

  public String getResourceId() {
    return resourceId;
  }

  public String getTitle() {
    return title;
  }
}

/** Interface for renewable resources. */
interface Renewable {
  boolean renewLoan(LibraryMember member);
}

/** Interface for reservable resources. */
interface Reservable {
  void reserve(LibraryMember member);

  void cancelReservation(LibraryMember member);
}

/** Class representing books. */
class Book extends LibraryResource implements Renewable, Reservable {
  public String author;
  public String isbn;

  public Book(final String resourceId, final String title, final String author, final String isbn) {
    super(resourceId, title);
    this.author = author;
    this.isbn = isbn;
  }

  @Override
  public double calculateLateFee(final int daysLate) {
    final double feeRate = 0.5;
    return daysLate * feeRate;
  }

  @Override
  public int getMaxLoanPeriod() {
    return 14;
  }

  @Override
  public boolean renewLoan(final LibraryMember member) {
    return getStatus() == ResourceStatus.BORROWED;
  }

  @Override
  public void reserve(final LibraryMember member) {
    //    if (getStatus() != ResourceStatus.AVAILABLE) {
    //      throw new IllegalStateException("Book is not available for reservation.");
    //    }
    setStatus(ResourceStatus.RESERVED);
  }

  @Override
  public void cancelReservation(final LibraryMember member) {
    if (getStatus() == ResourceStatus.RESERVED) {
      setStatus(ResourceStatus.AVAILABLE);
    }
  }
}

/** Class representing digital content. */
class DigitalContent extends LibraryResource implements Renewable {
  public double fileSize;
  public ContentFormat format;

  public DigitalContent(
      final String resourceId,
      final String title,
      final double fileSize,
      final ContentFormat format) {
    super(resourceId, title);
    this.fileSize = fileSize;
    this.format = format;
  }

  @Override
  public double calculateLateFee(final int daysLate) {
    final double feeRate = 0.25;
    return daysLate * feeRate;
  }

  @Override
  public int getMaxLoanPeriod() {
    return 7;
  }

  @Override
  public boolean renewLoan(final LibraryMember member) {
    return getStatus() == ResourceStatus.BORROWED;
  }
}

/** Class representing periodicals. */
class Periodical extends LibraryResource implements Renewable, Reservable {
  public int issueNumber;
  public Frequency frequency;

  public Periodical(
      final String resourceId,
      final String title,
      final int issueNumber,
      final Frequency frequency) {
    super(resourceId, title);
    this.issueNumber = issueNumber;
    this.frequency = frequency;
  }

  @Override
  public double calculateLateFee(final int daysLate) {
    final double feeRate = 0.3;
    return daysLate * feeRate;
  }

  @Override
  public int getMaxLoanPeriod() {
    return 7;
  }

  @Override
  public boolean renewLoan(final LibraryMember member) {
    return getStatus() == ResourceStatus.BORROWED;
  }

  @Override
  public void reserve(final LibraryMember member) {
    if (getStatus() != ResourceStatus.AVAILABLE) {
      throw new IllegalStateException("Periodical is not available for reservation.");
    }
    setStatus(ResourceStatus.RESERVED);
  }

  @Override
  public void cancelReservation(final LibraryMember member) {
    if (getStatus() == ResourceStatus.RESERVED) {
      setStatus(ResourceStatus.AVAILABLE);
    }
  }
}

/** Class representing library members. */
class LibraryMember {
  public String memberId;
  private MembershipType membershipType;
  private List<LibraryResource> borrowedResources;

  public LibraryMember(final String memberId, final MembershipType membershipType) {
    this.memberId = memberId;
    this.membershipType = membershipType;
    this.borrowedResources = new ArrayList<>();
  }

  public void borrowResource(final LibraryResource resource)
      throws ResourceNotAvailableException, MaximumLoanExceededException {
    final int limit = membershipType == MembershipType.STANDARD ? 5 : 10;
    if (borrowedResources.size() >= limit) {
      throw new MaximumLoanExceededException("Loan limit exceeded.");
    }
    if (resource.getStatus() != ResourceStatus.AVAILABLE) {
      throw new ResourceNotAvailableException("Resource not available.");
    }
    resource.setStatus(ResourceStatus.BORROWED);
    borrowedResources.add(resource);
  }

  public void returnResource(final LibraryResource resource) {
    resource.setStatus(ResourceStatus.AVAILABLE);
    borrowedResources.remove(resource);
  }

  public List<LibraryResource> getBorrowedResources() {
    return borrowedResources;
  }
}
