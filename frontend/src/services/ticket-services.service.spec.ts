import { TestBed } from '@angular/core/testing';

import { TicketServicesService } from './ticket-services.service';

describe('TicketServicesService', () => {
  let service: TicketServicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TicketServicesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
