import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListadoTecnicosComponent } from './listado-tecnicos.component';

describe('ListadoTecnicosComponent', () => {
  let component: ListadoTecnicosComponent;
  let fixture: ComponentFixture<ListadoTecnicosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListadoTecnicosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListadoTecnicosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
