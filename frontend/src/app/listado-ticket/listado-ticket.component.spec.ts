import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListadoTicketComponent } from './listado-ticket.component';

describe('ListadoTicketComponent', () => {
  let component: ListadoTicketComponent;
  let fixture: ComponentFixture<ListadoTicketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListadoTicketComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListadoTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
