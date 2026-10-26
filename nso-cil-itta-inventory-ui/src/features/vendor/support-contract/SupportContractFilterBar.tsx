import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { SUPPORT_CONTRACT_COVERAGE_LEVEL_OPTIONS, SUPPORT_CONTRACT_CRITICALITY_OPTIONS } from './types';
import type { SupportContractFilter } from './types';

interface Props {
  value: SupportContractFilter;
  onChange: (next: SupportContractFilter) => void;
  onReset: () => void;
}

export function SupportContractFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: SupportContractFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Support Contract filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Support Contract"
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
      <FilterField label="Vendor Id">
        <Input
          aria-label="Vendor Id filter"
          placeholder="UUID"
          value={value.vendorId ?? ''}
          onChange={(v) => patch({ vendorId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Contract Number">
        <Input
          aria-label="Contract Number filter"
          value={value.contractNumber ?? ''}
          onChange={(v) => patch({ contractNumber: v || undefined })}
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
      <FilterField label="Coverage Level">
        <MultiSelect
          ariaLabel="Coverage Level filter"
          options={SUPPORT_CONTRACT_COVERAGE_LEVEL_OPTIONS}
          value={value.coverageLevel ?? []}
          onChange={(v) => patch({ coverageLevel: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Annual Cost">
        <RangeInputs
          ariaLabel="Annual Cost"
          min={value.annualCostMin}
          max={value.annualCostMax}
          onChange={(min, max) => patch({ annualCostMin: min, annualCostMax: max })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={SUPPORT_CONTRACT_CRITICALITY_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
