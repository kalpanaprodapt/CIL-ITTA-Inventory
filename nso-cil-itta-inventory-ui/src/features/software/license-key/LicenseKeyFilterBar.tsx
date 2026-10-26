import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { LICENSE_KEY_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { LicenseKeyFilter } from './types';

interface Props {
  value: LicenseKeyFilter;
  onChange: (next: LicenseKeyFilter) => void;
  onReset: () => void;
}

export function LicenseKeyFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: LicenseKeyFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="License Key filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search License Key"
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
      <FilterField label="Pool Id">
        <Input
          aria-label="Pool Id filter"
          placeholder="UUID"
          value={value.poolId ?? ''}
          onChange={(v) => patch({ poolId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Key Value">
        <Input
          aria-label="Key Value filter"
          value={value.keyValue ?? ''}
          onChange={(v) => patch({ keyValue: v || undefined })}
        />
      </FilterField>
      <FilterField label="Feature">
        <Input
          aria-label="Feature filter"
          value={value.feature ?? ''}
          onChange={(v) => patch({ feature: v || undefined })}
        />
      </FilterField>
      <FilterField label="Expires On">
        <DateRange
          ariaLabel="Expires On"
          from={value.expiresOnFrom}
          to={value.expiresOnTo}
          onChange={(from, to) => patch({ expiresOnFrom: from, expiresOnTo: to })}
        />
      </FilterField>
      <FilterField label="Assigned Device Id">
        <Input
          aria-label="Assigned Device Id filter"
          placeholder="UUID"
          value={value.assignedDeviceId ?? ''}
          onChange={(v) => patch({ assignedDeviceId: v || undefined })}
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
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={LICENSE_KEY_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
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
