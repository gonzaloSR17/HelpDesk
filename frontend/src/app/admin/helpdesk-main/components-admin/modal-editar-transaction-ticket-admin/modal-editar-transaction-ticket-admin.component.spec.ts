import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalEditarTransactionTicketAdminComponent } from './modal-editar-transaction-ticket-admin.component';

describe('ModalEditarTransactionTicketAdminComponent', () => {
  let component: ModalEditarTransactionTicketAdminComponent;
  let fixture: ComponentFixture<ModalEditarTransactionTicketAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalEditarTransactionTicketAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalEditarTransactionTicketAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
