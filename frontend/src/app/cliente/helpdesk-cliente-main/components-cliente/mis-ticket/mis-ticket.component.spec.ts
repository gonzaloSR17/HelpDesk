import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MisTicketComponent } from './mis-ticket.component';

describe('MisTicketComponent', () => {
  let component: MisTicketComponent;
  let fixture: ComponentFixture<MisTicketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MisTicketComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MisTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
