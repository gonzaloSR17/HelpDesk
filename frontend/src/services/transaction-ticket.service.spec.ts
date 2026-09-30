import { TestBed } from '@angular/core/testing';

import { TransactionTicketService } from './transaction-ticket.service';

describe('TransactionTicketService', () => {
  let service: TransactionTicketService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransactionTicketService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
