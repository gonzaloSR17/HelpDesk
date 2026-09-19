import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HelpdeskTecnicoMainComponent } from './helpdesk-tecnico-main.component';

describe('HelpdeskTecnicoMainComponent', () => {
  let component: HelpdeskTecnicoMainComponent;
  let fixture: ComponentFixture<HelpdeskTecnicoMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HelpdeskTecnicoMainComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HelpdeskTecnicoMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
