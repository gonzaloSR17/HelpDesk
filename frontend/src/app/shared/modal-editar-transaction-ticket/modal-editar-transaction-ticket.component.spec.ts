import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalEditarTransactionTicketComponent } from './modal-editar-transaction-ticket.component';

describe('ModalEditarTransactionTicketComponent', () => {
  let component: ModalEditarTransactionTicketComponent;
  let fixture: ComponentFixture<ModalEditarTransactionTicketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalEditarTransactionTicketComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalEditarTransactionTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
