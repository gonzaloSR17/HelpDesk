import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HelpdeskClienteMainComponent } from './helpdesk-cliente-main.component';

describe('HelpdeskClienteMainComponent', () => {
  let component: HelpdeskClienteMainComponent;
  let fixture: ComponentFixture<HelpdeskClienteMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HelpdeskClienteMainComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HelpdeskClienteMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
