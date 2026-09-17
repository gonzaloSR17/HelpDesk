import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginSidebarComponent } from './login-sidebar.component';

describe('LoginSidebarComponent', () => {
  let component: LoginSidebarComponent;
  let fixture: ComponentFixture<LoginSidebarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginSidebarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
