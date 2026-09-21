import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MobileTopbarComponent } from './mobile-topbar.component';

describe('MobileTopbarComponent', () => {
  let component: MobileTopbarComponent;
  let fixture: ComponentFixture<MobileTopbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MobileTopbarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MobileTopbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
