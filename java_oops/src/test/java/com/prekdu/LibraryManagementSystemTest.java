package com.prekdu;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class LibraryManagementSystemTest {
  private LibraryMember standardMember;
  private LibraryMember premiumMember;
  private Book book;
  private DigitalContent digitalContent;

  @BeforeEach
  void setUp() {
    standardMember = new LibraryMember("STD001", MembershipType.STANDARD);
    premiumMember = new LibraryMember("PRE001", MembershipType.PREMIUM);
    book = new Book("B001", "Clean Code", "Robert Martin", "978-0132350884");
    digitalContent = new DigitalContent("D001", "Digital Design", 15.5, ContentFormat.PDF);
  }

  @Test
  void testBookLateFeeCalculation() {
    assertEquals(5.0, book.calculateLateFee(10), 0.01);
  }

  @Test
  void testDigitalContentLateFeeCalculation() {
    assertEquals(2.5, digitalContent.calculateLateFee(10), 0.01);
  }

  @Test
  void testStandardMemberBorrowLimit() {
    // Attempt to borrow more than the limit
    try {
      for (int i = 0; i < 5; i++) {
        Book newBook = new Book("B00" + i, "Test Book " + i, "Author", "ISBN");
        standardMember.borrowResource(newBook);
      }

      Book extraBook = new Book("B006", "Extra Book", "Author", "ISBN");
      assertThrows(
          MaximumLoanExceededException.class,
          () -> {
            standardMember.borrowResource(extraBook);
          });
    } catch (Exception e) {
      fail("Exception should not have been thrown: " + e.getMessage());
    }
  }

  @Test
  void testPremiumMemberBorrowLimit() {
    // Premium members should be able to borrow 10 items
    try {
      for (int i = 0; i < 10; i++) {
        Book newBook = new Book("B00" + i, "Test Book " + i, "Author", "ISBN");
        premiumMember.borrowResource(newBook);
      }

      assertEquals(10, premiumMember.getBorrowedResources().size());
    } catch (Exception e) {
      fail("Exception should not have been thrown: " + e.getMessage());
    }
  }

  //  @Test
  //  void testBorrowAndReturn() {
  //    standardMember.borrowResource(book);
  //    assertEquals(ResourceStatus.BORROWED, book.getStatus());
  //    assertEquals(1, standardMember.getBorrowedResources().size());
  //
  //    standardMember.returnResource(book);
  //    assertEquals(ResourceStatus.AVAILABLE, book.getStatus());
  //    assertEquals(0, standardMember.getBorrowedResources().size());
  //  }
  @Test
  void testBorrowAndReturn() {
    try {
      standardMember.borrowResource(book); // May throw exception
      assertEquals(ResourceStatus.BORROWED, book.getStatus());
      assertEquals(1, standardMember.getBorrowedResources().size());

      standardMember.returnResource(book);
      assertEquals(ResourceStatus.AVAILABLE, book.getStatus());
      assertEquals(0, standardMember.getBorrowedResources().size());
    } catch (Exception e) {
      fail("Exception should not have been thrown: " + e.getMessage());
    }
  }

  @Test
  void testBookReservation() throws ResourceNotAvailableException, MaximumLoanExceededException {
    standardMember.borrowResource(book);
    LibraryMember anotherMember = new LibraryMember("STD002", MembershipType.STANDARD);

    book.reserve(anotherMember);
    assertFalse(book.renewLoan(standardMember));
  }

  @Test
  void testResourceAvailability() {
    try {
      standardMember.borrowResource(book);

      LibraryMember anotherMember = new LibraryMember("STD002", MembershipType.STANDARD);
      assertThrows(
          ResourceNotAvailableException.class,
          () -> {
            anotherMember.borrowResource(book);
          });
    } catch (Exception e) {
      fail("Exception should not have been thrown: " + e.getMessage());
    }
  }

  @Test
  void testDigitalContentRenewal() {
    try {
      standardMember.borrowResource(digitalContent);
      assertTrue(digitalContent.renewLoan(standardMember));
    } catch (Exception e) {
      fail("Exception should not have been thrown: " + e.getMessage());
    }
  }

  @Test
  void testInvalidReservation() {
    //    try {
    //      assertThrows(
    //              IllegalStateException.class,
    //              () -> {
    //                book.reserve(standardMember);
    //              });
    //    }
    //    catch (Exception e) {
    //      fail("Exception should not have been thrown: " + e.getMessage());
    //    }
  }
}
