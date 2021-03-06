import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditScaComponent } from './edit-sca.component';

describe('EditScaComponent', () => {
  let component: EditScaComponent;
  let fixture: ComponentFixture<EditScaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditScaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditScaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
