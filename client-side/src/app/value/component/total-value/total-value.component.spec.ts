import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TotalValueComponent } from './total-value.component';

describe('TotalValueComponent', () => {
  let component: TotalValueComponent;
  let fixture: ComponentFixture<TotalValueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TotalValueComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TotalValueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
