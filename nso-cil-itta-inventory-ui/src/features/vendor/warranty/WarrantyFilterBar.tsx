import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { WARRANTY_WARRANTY_TYPE_OPTIONS } from './types';
import type { WarrantyFilter } from './types';

interface Props {
  value: WarrantyFilter;
  onChange: (next: WarrantyFilter) => void;
  onReset: () => void;
}

export function WarrantyFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: WarrantyFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Warranty filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Warranty"
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
      <FilterField label="Asset Id">
        <Input
          aria-label="Asset Id filter"
          placeholder="UUID"
          value={value.assetId ?? ''}
          onChange={(v) => patch({ assetId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Start Date">
        <DateRange
          ariaLabel="Start Date"
          from={value.startDateFrom}
          to={value.startDateTo}
          onChange={(from, to) => patch({ startDateFrom: from, startDateTo: to })}
        />
      </FilterField>
      <FilterField label="End Date">
        <DateRange
          ariaLabel="End Date"
          from={value.endDateFrom}
          to={value.endDateTo}
          onChange={(from, to) => patch({ endDateFrom: from, endDateTo: to })}
        />
      </FilterField>
      <FilterField label="Warranty Type">
        <MultiSelect
          ariaLabel="Warranty Type filter"
          options={WARRANTY_WARRANTY_TYPE_OPTIONS}
          value={value.warrantyType ?? []}
          onChange={(v) => patch({ warrantyType: v.length ? v : undefined })}
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
      <FilterField label="Labels">
        <Input
          aria-label="Labels filter"
          value={value.labels ?? ''}
          onChange={(v) => patch({ labels: v || undefined })}
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
