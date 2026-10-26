import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { TECHNICIAN_CRITICALITY_OPTIONS } from './types';
import type { TechnicianFilter } from './types';

interface Props {
  value: TechnicianFilter;
  onChange: (next: TechnicianFilter) => void;
  onReset: () => void;
}

export function TechnicianFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: TechnicianFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Technician filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Technician"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Employee Id">
        <Input
          aria-label="Employee Id filter"
          value={value.employeeId ?? ''}
          onChange={(v) => patch({ employeeId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Full Name">
        <Input
          aria-label="Full Name filter"
          value={value.fullName ?? ''}
          onChange={(v) => patch({ fullName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Skill Set">
        <Input
          aria-label="Skill Set filter"
          value={value.skillSet ?? ''}
          onChange={(v) => patch({ skillSet: v || undefined })}
        />
      </FilterField>
      <FilterField label="Home Site Id">
        <Input
          aria-label="Home Site Id filter"
          placeholder="UUID"
          value={value.homeSiteId ?? ''}
          onChange={(v) => patch({ homeSiteId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Certification Level">
        <RangeInputs
          ariaLabel="Certification Level"
          min={value.certificationLevelMin}
          max={value.certificationLevelMax}
          onChange={(min, max) => patch({ certificationLevelMin: min, certificationLevelMax: max })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={TECHNICIAN_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Cost Center">
        <Input
          aria-label="Cost Center filter"
          value={value.costCenter ?? ''}
          onChange={(v) => patch({ costCenter: v || undefined })}
        />
      </FilterField>
      <FilterField label="Effective From">
        <DateRange
          ariaLabel="Effective From"
          from={value.effectiveFromFrom}
          to={value.effectiveFromTo}
          onChange={(from, to) => patch({ effectiveFromFrom: from, effectiveFromTo: to })}
        />
      </FilterField>
      <FilterField label="Effective To">
        <DateRange
          ariaLabel="Effective To"
          from={value.effectiveToFrom}
          to={value.effectiveToTo}
          onChange={(from, to) => patch({ effectiveToFrom: from, effectiveToTo: to })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
