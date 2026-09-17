import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelTicketComponent } from './panel-ticket.component';

describe('PanelTicketComponent', () => {
  let component: PanelTicketComponent;
  let fixture: ComponentFixture<PanelTicketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PanelTicketComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PanelTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
