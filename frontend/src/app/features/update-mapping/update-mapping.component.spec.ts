import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateMappingComponent } from './update-mapping.component';

describe('UpdateMappingComponent', () => {
  let component: UpdateMappingComponent;
  let fixture: ComponentFixture<UpdateMappingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateMappingComponent]
    });
    fixture = TestBed.createComponent(UpdateMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
