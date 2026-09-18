import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginHelpdeskComponent } from './login-helpdesk.component';

describe('LoginHelpdeskComponent', () => {
  let component: LoginHelpdeskComponent;
  let fixture: ComponentFixture<LoginHelpdeskComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginHelpdeskComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginHelpdeskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
