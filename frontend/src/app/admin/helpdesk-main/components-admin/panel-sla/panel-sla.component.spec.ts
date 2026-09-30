import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelSlaComponent } from './panel-sla.component';

describe('PanelSlaComponent', () => {
  let component: PanelSlaComponent;
  let fixture: ComponentFixture<PanelSlaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PanelSlaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PanelSlaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
