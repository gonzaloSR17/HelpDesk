import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HelpdeskMainComponent } from './helpdesk-main.component';

describe('HelpdeskMainComponent', () => {
  let component: HelpdeskMainComponent;
  let fixture: ComponentFixture<HelpdeskMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HelpdeskMainComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HelpdeskMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
