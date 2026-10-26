import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { DISCOVERY_PROFILE_PROTOCOL_OPTIONS } from './types';
import type { DiscoveryProfileFilter } from './types';

interface Props {
  value: DiscoveryProfileFilter;
  onChange: (next: DiscoveryProfileFilter) => void;
  onReset: () => void;
}

export function DiscoveryProfileFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: DiscoveryProfileFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Discovery Profile filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Discovery Profile"
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
      <FilterField label="Profile Name">
        <Input
          aria-label="Profile Name filter"
          value={value.profileName ?? ''}
          onChange={(v) => patch({ profileName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Protocol">
        <MultiSelect
          ariaLabel="Protocol filter"
          options={DISCOVERY_PROFILE_PROTOCOL_OPTIONS}
          value={value.protocol ?? []}
          onChange={(v) => patch({ protocol: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Credential Ref">
        <Input
          aria-label="Credential Ref filter"
          value={value.credentialRef ?? ''}
          onChange={(v) => patch({ credentialRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Scope Cidr">
        <Input
          aria-label="Scope Cidr filter"
          value={value.scopeCidr ?? ''}
          onChange={(v) => patch({ scopeCidr: v || undefined })}
        />
      </FilterField>
      <FilterField label="Schedule Cron">
        <Input
          aria-label="Schedule Cron filter"
          value={value.scheduleCron ?? ''}
          onChange={(v) => patch({ scheduleCron: v || undefined })}
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
