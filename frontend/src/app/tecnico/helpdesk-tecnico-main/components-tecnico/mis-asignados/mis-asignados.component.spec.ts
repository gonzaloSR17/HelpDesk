import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MisAsignadosComponent } from './mis-asignados.component';

describe('MisAsignadosComponent', () => {
  let component: MisAsignadosComponent;
  let fixture: ComponentFixture<MisAsignadosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MisAsignadosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MisAsignadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
