import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { CUSTOMER_CONTACT_CONTACT_ROLE_OPTIONS, CUSTOMER_CONTACT_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { CustomerContactFilter } from './types';

interface Props {
  value: CustomerContactFilter;
  onChange: (next: CustomerContactFilter) => void;
  onReset: () => void;
}

export function CustomerContactFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: CustomerContactFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Customer Contact filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Customer Contact"
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
      <FilterField label="Customer Id">
        <Input
          aria-label="Customer Id filter"
          placeholder="UUID"
          value={value.customerId ?? ''}
          onChange={(v) => patch({ customerId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Full Name">
        <Input
          aria-label="Full Name filter"
          value={value.fullName ?? ''}
          onChange={(v) => patch({ fullName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Email">
        <Input
          aria-label="Email filter"
          value={value.email ?? ''}
          onChange={(v) => patch({ email: v || undefined })}
        />
      </FilterField>
      <FilterField label="Phone">
        <Input
          aria-label="Phone filter"
          value={value.phone ?? ''}
          onChange={(v) => patch({ phone: v || undefined })}
        />
      </FilterField>
      <FilterField label="Contact Role">
        <MultiSelect
          ariaLabel="Contact Role filter"
          options={CUSTOMER_CONTACT_CONTACT_ROLE_OPTIONS}
          value={value.contactRole ?? []}
          onChange={(v) => patch({ contactRole: v.length ? v : undefined })}
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
          options={CUSTOMER_CONTACT_LIFECYCLE_STAGE_OPTIONS}
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
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
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
