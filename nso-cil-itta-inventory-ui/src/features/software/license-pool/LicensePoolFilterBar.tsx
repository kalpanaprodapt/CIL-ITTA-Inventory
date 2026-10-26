import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { LICENSE_POOL_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { LicensePoolFilter } from './types';

interface Props {
  value: LicensePoolFilter;
  onChange: (next: LicensePoolFilter) => void;
  onReset: () => void;
}

export function LicensePoolFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: LicensePoolFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="License Pool filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search License Pool"
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
      <FilterField label="Pool Name">
        <Input
          aria-label="Pool Name filter"
          value={value.poolName ?? ''}
          onChange={(v) => patch({ poolName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Total Seats">
        <RangeInputs
          ariaLabel="Total Seats"
          min={value.totalSeatsMin}
          max={value.totalSeatsMax}
          onChange={(min, max) => patch({ totalSeatsMin: min, totalSeatsMax: max })}
        />
      </FilterField>
      <FilterField label="Used Seats">
        <RangeInputs
          ariaLabel="Used Seats"
          min={value.usedSeatsMin}
          max={value.usedSeatsMax}
          onChange={(min, max) => patch({ usedSeatsMin: min, usedSeatsMax: max })}
        />
      </FilterField>
      <FilterField label="Renewal Date">
        <DateRange
          ariaLabel="Renewal Date"
          from={value.renewalDateFrom}
          to={value.renewalDateTo}
          onChange={(from, to) => patch({ renewalDateFrom: from, renewalDateTo: to })}
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
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={LICENSE_POOL_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
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
