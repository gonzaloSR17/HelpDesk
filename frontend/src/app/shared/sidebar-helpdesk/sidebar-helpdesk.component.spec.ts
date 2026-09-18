import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarHelpdeskComponent } from './sidebar-helpdesk.component';

describe('SidebarHelpdeskComponent', () => {
  let component: SidebarHelpdeskComponent;
  let fixture: ComponentFixture<SidebarHelpdeskComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SidebarHelpdeskComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SidebarHelpdeskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
